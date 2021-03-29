import {AfterViewInit, Component, ElementRef, ViewChild} from '@angular/core';
import * as d3 from 'd3';
import {CollapsibleNode} from '../../core/model/collapsible-node';

@Component({
  selector: 'app-tree-view',
  templateUrl: './tree-view.component.html',
  styleUrls: ['./tree-view.component.scss']
})
export class TreeViewComponent implements AfterViewInit {

  @ViewChild('canvas')
  canvas: ElementRef;

  width = 0;
  height = 0;
  margin = {top: 20, right: 90, bottom: 30, left: 90};
  tree: d3.TreeLayout<any>;
  g: d3.Selection<SVGGElement, unknown, HTMLElement, any>;
  root: CollapsibleNode;
  zoomBehavior: d3.ZoomBehavior<any, any>;

  // TODO
  i = 0;
  treeData = {
    name: 'Top Level',
    children: [
      {
        name: 'Level 2: A',
        children: [
          { name: 'Son of A', children: [] },
          { name: 'Daughter of A', children: [] }
        ]
      },
      { name: 'Level 2: B', children: [] }
    ]
  };

  constructor() { }

  ngAfterViewInit(): void {
    const rect: ClientRect = this.canvas.nativeElement.getBoundingClientRect();
    this.width = rect.width;
    this.height = rect.height;

    this.setupCanvas();
    this.setupTree();
  }

  setupCanvas(): void {
    const svg = d3.select('.canvas')
      .append('svg');
    this.g = svg.attr('width', this.width)
      .attr('height', this.height)
      .append('g')
      .attr('transform', `translate(${this.margin.left}, ${this.margin.top})`);

    const zoom = d3.zoom().scaleExtent([1, 1])
      .on('zoom', event => {
        this.g.selectAll('path')
          .attr('transform', event.transform);
        this.g.selectAll('circle')
          .attr('transform', (node: any) => {
            const transformed = event.transform;
            return transformed;
          });
        this.g.selectAll('text')
          .attr('transform', event.transform);
      });

    svg.call(zoom);
  }

  setupTree(): void {
    this.tree = d3.tree().size([this.height, this.width]);
    this.root = d3.hierarchy(this.treeData, d => d.children) as d3.HierarchyRectangularNode<any>;
    this.root.x0 = 0;
    this.root.y0 = this.width / 2;

    this.root.children.forEach(it => this.collapse(it));

    this.update(this.root);

  }

  collapse(d: CollapsibleNode): void {
    if (d.children) {
      d._children = d.children;
      d._children.forEach(it => this.collapse(it));
      d.children = null;
    }
  }

  update(source: any): void {
    const duration = 750;

    const treeData = this.tree(this.root);

    const nodes = treeData.descendants();
    const links = treeData.descendants().slice(1);

    nodes.forEach(d => { d.y = d.depth * 210; });

    // ****************** Nodes section ***************************

    // Update the nodes...
    const node = this.g.selectAll('g.node')
      .data(nodes, (d: any) => d.id || (d.id = ++this.i) );

    // // Enter any new modes at the parent's previous position.
    const nodeEnter = node.enter().append('g')
      .attr('class', 'node')
      .attr('transform', () => `translate(${source.x0}, ${source.y0})`)
      .on('click', (_, d) => this.click(d));
    //
    // // Add Circle for the nodes
    nodeEnter.append('circle')
      .attr('class', 'node')
      .attr('r', 1e-6)
      .style('fill', (d: CollapsibleNode) => d._children ? 'lightsteelblue' : '#fff');

    // // Add labels for the nodes
    nodeEnter.append('text')
      .attr('dy', '.35em')
      .attr('x', (d: CollapsibleNode) => d.children || d._children ? -13 : 13)
      .attr('text-anchor', (d: CollapsibleNode) => d.children || d._children ? 'end' : 'start')
      .text(d =>  d.data.name);
    //
    // @ts-ignore
    const nodeUpdate = nodeEnter.merge(node);
    //
    // Transition to the proper position for the node
    nodeUpdate.transition()
      .duration(duration)
      .attr('transform', d => `translate(${d.x}, ${d.y})`);

    // // Update the node attributes and style
    nodeUpdate.select('circle.node')
      .attr('r', 10)
      .style('fill', (d: CollapsibleNode) => d._children ? 'lightsteelblue' : '#fff')
      .attr('cursor', 'pointer');
    //

    // // Remove any exiting nodes
    const nodeExit = node.exit().transition()
      .duration(duration)
      .attr('transform', () => `translate(${source.x}, ${source.y})`);
    //
    // // On exit reduce the node circles size to 0
    nodeExit.select('circle')
      .attr('r', 1e-6);
    //
    // // On exit reduce the opacity of text labels
    nodeExit.select('text')
      .style('fill-opacity', 1e-6);

    // ****************** links section ***************************

    // Update the links...
    const link = this.g.selectAll('path.link')
      // @ts-ignore
      .data(links, d => d.id);

    // Enter any new links at the parent's previous position.
    const linkEnter = link.enter().insert('path', 'g')
      .attr('class', 'link')
      .attr('d', d => {
        const o = {x: source.x0, y: source.y0};
        return this.diagonal(o, o);
      });

    // UPDATE
    // @ts-ignore
    const linkUpdate = linkEnter.merge(link);

    // Transition back to the parent element position
    linkUpdate.transition()
      .duration(duration)
      .attr('d', d => this.diagonal(d, d.parent));

    // Remove any exiting links
    const linkExit = link.exit().transition()
      .duration(duration)
      .attr('d', d => {
        const o = {x: source.x, y: source.y};
        return this.diagonal(o, o);
      });

    // Store the old positions for transition.
    nodes.forEach((d: CollapsibleNode) => {
      d.x0 = d.x;
      d.y0 = d.y;
    });

  }

  diagonal(s: any, d: any): string {
    return `M ${s.x}, ${s.y}
            C ${s.x}, ${(s.y + d.y) / 2}
            ${d.x}, ${(s.y + d.y) / 2}
            ${d.x}, ${d.y}`;
  }

  click(d: any): void {
    if (d.children) {
      d._children = d.children;
      d.children = null;
    } else {
      d.children = d._children;
      d._children = null;
    }
    this.update(d);
  }
}
