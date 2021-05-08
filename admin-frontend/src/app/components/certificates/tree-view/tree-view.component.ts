import { AfterViewInit, Component, ElementRef, EventEmitter, Output, ViewChild } from '@angular/core';
import * as d3 from 'd3';
import { CollapsibleNode } from '../../../core/model/collapsible-node';
import { CertificateService } from '../../../core/services/certificate.service';
import { CertificateInfo } from '../../../core/model/certificate-info';
import { MenuItem } from 'primeng/api';
import { ContextMenu } from 'primeng/contextmenu';
import { ROOT_ALIAS } from 'src/app/core/utils/constants';

@Component({
  selector: 'app-tree-view',
  templateUrl: './tree-view.component.html',
  styleUrls: ['./tree-view.component.scss']
})
export class TreeViewComponent implements AfterViewInit {

  @ViewChild('canvas') canvas: ElementRef;
  @ViewChild('contextMenu') contextMenu: ContextMenu;
  @Output() openCertificate: EventEmitter<CertificateInfo> = new EventEmitter<CertificateInfo>();
  @Output() revokeCertificate: EventEmitter<CertificateInfo> = new EventEmitter<CertificateInfo>();
  @Output() downloadCrt: EventEmitter<CertificateInfo> = new EventEmitter<CertificateInfo>();
  @Output() downloadKey: EventEmitter<CertificateInfo> = new EventEmitter<CertificateInfo>();
  @Output() downloadJks: EventEmitter<CertificateInfo> = new EventEmitter<CertificateInfo>();
  @Output() switchCA: EventEmitter<CertificateInfo> = new EventEmitter<CertificateInfo>();

  menuItems: MenuItem[] = [
    {icon: 'pi pi-info', label: 'Details', command: () => this.openCertificate.emit(this.context)},
    {icon: 'pi pi-download', label: '.crt', command: () => this.downloadCrt.emit(this.context)},
    {icon: 'pi pi-download', label: '.key', command: () => this.downloadKey.emit(this.context)},
    {icon: 'pi pi-download', label: '.jks', command: () => this.downloadJks.emit(this.context)}
  ];
  oldMenuItems: MenuItem[] = [
    {icon: 'pi pi-info', label: 'Details', command: () => this.openCertificate.emit(this.context)},
    {icon: 'pi pi-download', label: '.crt', command: () => this.downloadCrt.emit(this.context)},
    {icon: 'pi pi-download', label: '.key', command: () => this.downloadKey.emit(this.context)},
    {icon: 'pi pi-download', label: '.jks', command: () => this.downloadJks.emit(this.context)}
  ];

  g: d3.Selection<SVGGElement, unknown, HTMLElement, any>;
  tree: d3.TreeLayout<any>;
  root: CollapsibleNode;
  width = 0;
  height = 0;

  context: CertificateInfo;
  noChildren = false;

  constructor(private certificateService: CertificateService) { }

  ngAfterViewInit(): void {
    if (this.certificateService.ca.alias !== ROOT_ALIAS) {
      // tslint:disable-next-line: deprecation
      this.certificateService.findByAlias(ROOT_ALIAS).subscribe((ca: CertificateInfo) => {
        this.certificateService.ca = ca;
        this.setup();
      });
    }
    else{
      this.setup();
    }
  }

  reset(): void {
    // tslint:disable-next-line: deprecation
    this.certificateService.findByAlias(ROOT_ALIAS).subscribe((ca: CertificateInfo) => {
      this.certificateService.ca = ca;
      this.setupTree();
    });
  }

  private update(source: CollapsibleNode): void {
    const duration = 400;
    const treeData = this.tree(this.root);
    const nodes = treeData.descendants();
    const links = treeData.descendants().splice(1);
    nodes.forEach(n => { n.y = n.depth * 180; });

    // ****************** Nodes section ***************************

    // Update the nodes
    const node = this.g.selectAll('g.node')
      .data(nodes, (d: any) => `N${d.data.id}`);

    // // Enter any new modes at the parent's previous position.
    const nodeEnter = node.enter().append('g')
      .attr('class', 'node')
      .attr('id', d => `N${d.data.id}`)
      .attr('transform', `translate(${source.x0}, ${source.y0})`)
      .on('click', (ev, d) => this.click(ev, d))
      .on('contextmenu', (ev, d) => this.openContextMenu(ev, d));

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
      .text(d =>  d.data.alias);

    // @ts-ignore
    const nodeUpdate = nodeEnter.merge(node);
    // Transition to the proper position for the node
    nodeUpdate.transition()
      .duration(duration)
      .attr('transform', d => `translate(${d.x}, ${d.y})`);
    // // Update the node attributes and style
    nodeUpdate.select('circle.node')
      .attr('r', 10)
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
      .attr('transform', `translate(${source.x}, ${source.y})`)
      .remove();
    nodeExit.select('circle')
      .attr('r', 1e-6);
    // // On exit reduce the opacity of text labels
    nodeExit.select('text')
      .style('fill-opacity', 0);

    // ****************** links section ***************************

    // Update the links
    const link = this.g.selectAll('path.link')
      .data(links, (d: any) => `L${d.data.alias}`);

    // Enter any new links at the parent's previous position.
    const linkEnter = link.enter().insert('path', 'g')
      .attr('class', 'link')
      .attr('d', () => {
        const o = {x: source.x0, y: source.y0};
        return this.diagonal(o, o);
      });

    // @ts-ignore
    const linkUpdate = linkEnter.merge(link);

    // Transition back to the parent element position
    linkUpdate.transition()
      .duration(duration)
      .attr('d', d => this.diagonal(d, d.parent));

    // Remove any exiting links
    const linkExit = link.exit().transition()
      .duration(duration)
      .attr('d', () => {
        const o = {x: source.x, y: source.y};
        return this.diagonal(o, o);
      })
      .remove();

    nodes.forEach((d: CollapsibleNode) => {
      d.x0 = d.x;
      d.y0 = d.y;
    });
  }

  private setup(): void{
    const rect: ClientRect = this.canvas.nativeElement.getBoundingClientRect();
    this.width = rect.width;
    this.height = rect.height;
    this.setupCanvas();
    this.setupTree();
  }

  private setupCanvas(): void {
    const svg = d3.select('.canvas')
      .append('svg').on('contextmenu', ev => this.svgContextMenu(ev));

    const outerMain = svg.attr('width', this.width)
      .attr('height', this.height)
      .append('g')
      .attr('id', 'outerMain');

    this.g = outerMain.append('g')
      .attr('id', 'main')
      .attr('transform', `translate(${Math.floor(this.height / 10)}, ${Math.floor(this.width / 10)})`);

    const zoom = d3.zoom().scaleExtent([.3, 5])
      .on('zoom', event => {
        svg.select('#outerMain')
          .attr('transform', event.transform);
      });

    svg.call(zoom).on('dblclick.zoom', null);
  }

  private setupTree(): void {
    this.tree = d3.tree().size([this.height, this.width]);
    this.root = d3.hierarchy(this.certificateService.ca, d => d.issued) as CollapsibleNode;
    this.root.x0 = this.height / 2;
    this.root.y0 = 0;

    this.root.children.forEach(it => this.collapse(it));
    this.update(this.root);
    if (!this.root.children) {
      this.noChildren = true;
    }
  }

  private click(ev: Event, d: any): void {
    ev.preventDefault();
    ev.stopPropagation();
    if (!!d.children) {
      d._children = d.children;
      d.children = null;
      this.update(d);
    }
    else if (!!d._children) {
      d.children = d._children;
      d._children = null;
      this.update(d);
    }
    else if (d.data.numIssued > 0) {
      this.loadNode(d);
    }
  }

  private diagonal(s: any, d: any): string {
    return `M ${s.x}, ${s.y}
            C ${s.x}, ${(s.y + d.y) / 2}
            ${d.x}, ${(s.y + d.y) / 2}
            ${d.x}, ${d.y}`;
  }

  private collapse(d: CollapsibleNode): void {
    if (d.children) {
      d._children = d.children;
      d._children.forEach(it => this.collapse(it));
      d.children = null;
    }
  }

  private loadNode(d: CollapsibleNode): void {
    const node = this.g.select(`#N${d.data.id} circle`);
    node.transition().delay(50)
      .attr('r', 12)
      .style('fill', 'red');

    // tslint:disable-next-line: deprecation
    this.certificateService.findByAlias(d.data.alias).subscribe((response: any) => {
      const children = response.issued.map(ci => d3.hierarchy(ci, j => j.issued));
      for (const child of children) {
        child[`depth`] = d.depth + 1;
        child.parent = d;
      }
      d.children = children;
      node.transition().delay(50)
        .attr('r', 10)
        .style('fill', '#fff');
      this.update(d);
    });
  }

  private openContextMenu(ev: MouseEvent, d: any): void {
    ev.preventDefault();
    ev.stopPropagation();
    this.menuItems = [...this.oldMenuItems];
    this.context = d.data;
    if (this.context.alias !== this.certificateService.ca.alias &&
      this.context.alias !== ROOT_ALIAS &&
      !this.context.revoked) {
      this.menuItems.push({icon: 'pi pi-trash', label: 'Revoke', command: () => this.revokeCertificate.emit(this.context)});
    }
    if (this.context.alias !== this.certificateService.ca.alias &&
      this.context.template === 'SUB_CA' &&
      !this.context.revoked) {
      this.menuItems.push({icon: 'pi pi-replay', label: 'Use CA', command: () => this.switchCA.emit(this.context)});
    }
    this.contextMenu.show(ev);
  }

  private svgContextMenu(ev: MouseEvent): void {
    ev.preventDefault();
    this.menuItems = [...this.oldMenuItems];
    this.context = null;
    this.contextMenu.hide();
  }

}
