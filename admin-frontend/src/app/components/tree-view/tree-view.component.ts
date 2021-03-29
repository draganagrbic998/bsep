import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import * as d3 from 'd3';
import {CollapsibleNode} from '../../core/model/collapsible-node';
import {CertificateService} from '../../core/services/certificate.service';
import {CertificateInfo} from '../../core/model/certificate-info';

@Component({
  selector: 'app-tree-view',
  templateUrl: './tree-view.component.html',
  styleUrls: ['./tree-view.component.scss']
})
export class TreeViewComponent implements OnInit, AfterViewInit {

  @ViewChild('canvas')
  canvas: ElementRef;

  width = 0;
  height = 0;
  margin = {top: 20, right: 90, bottom: 30, left: 90};
  tree: d3.TreeLayout<any>;
  g: d3.Selection<SVGGElement, unknown, HTMLElement, any>;
  root: CollapsibleNode;
  zoomBehavior: d3.ZoomBehavior<any, any>;
  loading = false;
  noChildren = false;

  // TODO
  root2: CollapsibleNode;
  i = 0;
  ca: CertificateInfo | null = null;
  treeData =
    {
      name: 'Top Level',
      children: [
        {
          name: 'Level 2: A',
          children: [
            { name: 'Son of A' },
            { name: 'Daughter of A' }
          ]
        },
        { name: 'Level 2: B' }
      ]
    };

  constructor(private certificateService: CertificateService) { }

  ngOnInit(): void {
    this.loading = true;
  }

  ngAfterViewInit(): void {

    this.certificateService.ca.subscribe(val => {
      if (val.alias !== 'root') {
        this.loading = true;
        return;
      }
      this.ca = val;
      this.loading = false;
    });

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
          .attr('transform', event.transform);
        this.g.selectAll('text')
          .attr('transform', event.transform);
      });

    svg.call(zoom);
  }

  setupTree(): void {
    this.tree = d3.tree().size([this.height, this.width]);
    this.root = d3.hierarchy(this.ca, d => d.issued) as CollapsibleNode;

    if (!this.root.children) {
      this.noChildren = true;
    }


    this.root.children.forEach(it => this.collapse(it));

    this.update();

  }

  collapse(d: CollapsibleNode): void {
    if (d.children) {
      d._children = d.children;
      d._children.forEach(it => this.collapse(it));
      d.children = null;
    }
  }

  update(): void {
    const duration = 100;

    const treeData = this.tree(this.root);

    const nodes = treeData.descendants();
    const links = treeData.links();

    nodes.forEach(n => { n.y = n.depth * 210; });

    // ****************** Nodes section ***************************

    // Update the nodes...
    const node = this.g.selectAll('g.node')
      .data(nodes, (d: any) => `N${d.data.id}`);

    // // Enter any new modes at the parent's previous position.
    const nodeEnter = node.enter().append('g')
      .attr('class', 'node')
      .attr('id', d => `N${d.data.id}`)
      .attr('transform', d => {
        return `translate(${d.x}, ${d.y})`;
      })
      .style('opacity', 0)
      .on('click', (ev, d) => this.click(ev, d));

    // // Add Circle for the nodes
    nodeEnter.append('circle')
      .attr('class', 'node')
      .attr('r', 10)
      .style('fill', (d: CollapsibleNode) => d._children ? 'lightsteelblue' : '#fff');

    // // Add labels for the nodes
    nodeEnter.append('text')
      .attr('dy', '.35em')
      .attr('x', (d: CollapsibleNode) => d.children || d._children ? -13 : 13)
      .attr('text-anchor', (d: CollapsibleNode) => d.children || d._children ? 'end' : 'start')
      .text(d =>  d.data.alias);

    // @ts-ignore
    const nodeUpdate = nodeEnter.merge(node);
    // Transition to the proper position for the node
    nodeUpdate.transition()
      .duration(duration)
      .style('opacity', 1);

    // // Update the node attributes and style
    nodeUpdate.select('circle.node')
      .style('fill', (d: CollapsibleNode) => {
        if (!d._children && !d.children && d.data.numIssued > 0) {
          return 'lightsteelblue';
        }
        if (!!d._children && d._children.length > 0) {
          return 'lightsteelblue';
        }
        return '#fff';
      })
      .attr('cursor', 'pointer');

    nodeUpdate.select('text')
      .style('fill-opacity', 1);


    // // Remove any exiting nodes
    const nodeExit = node.exit().transition()
      .duration(duration)
      .style('opacity', 0);

    //
    // // On exit reduce the opacity of text labels
    nodeExit.select('text')
      .style('fill-opacity', 0);

    // ****************** links section ***************************

    // Update the links...
    const link = this.g.selectAll('path.link')
      .data(links, (d: any) => `L${d.target.data.id}`);

    // Enter any new links at the parent's previous position.
    const linkEnter = link.enter().insert('path', 'g')
      .attr('class', 'link')
      .style('opacity', 0)
      .attr('d', d => {
        return this.diagonal(d.source, d.target);
      });

    // UPDATE
    // @ts-ignore
    const linkUpdate = linkEnter.merge(link);

    // Transition back to the parent element position
    linkUpdate.transition()
      .duration(duration)
      .style('opacity', 1);

    // Remove any exiting links
    const linkExit = link.exit().transition()
      .duration(duration)
      .style('opacity', 0);


  }

  diagonal(s: any, d: any): string {
    return `M ${s.x}, ${s.y}
            C ${s.x}, ${(s.y + d.y) / 2}
            ${d.x}, ${(s.y + d.y) / 2}
            ${d.x}, ${d.y}`;
  }

  click(ev: Event, d: any): void {
    ev.preventDefault();
    if (!!d.children) {
      d._children = d.children;
      d.children = null;
      this.update();
    } else if (!!d._children) {
      d.children = d._children;
      d._children = null;
      this.update();
    } else if (d.data.numIssued > 0) {
      this.loadNode(d);
    }
  }

  loadNode(d: CollapsibleNode): void {
    const node = this.g.select(`#N${d.data.id} circle`);
    node.transition().delay(50)
      .attr('r', 12)
      .style('fill', 'red');

    this.certificateService.getByAlias(d.data.alias).subscribe(val => {
      const children = val.issued.map(i => d3.hierarchy(i, j => j.issued));

      for (const child of children) {
        // @ts-ignore
        child.depth = d.depth + 1;
        child.parent = d;
      }
      d.children = children;
      node.transition().delay(50)
        .attr('r', 10)
        .style('fill', '#fff');
      this.update();
    });
  }

  async animateNode(d: CollapsibleNode): void {

    while (this.loading) {

    }
  }
}
