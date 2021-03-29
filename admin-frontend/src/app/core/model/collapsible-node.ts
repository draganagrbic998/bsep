import * as d3 from 'd3';

export interface CollapsibleNode extends d3.HierarchyNode<any> {
  _children?: this[];
  x0?: number;
  y0?: number;
  x?: number;
  y?: number;
}
