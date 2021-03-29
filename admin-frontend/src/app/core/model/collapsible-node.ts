import * as d3 from 'd3';

export interface CollapsibleNode extends d3.HierarchyNode<any> {
  _children?: CollapsibleNode[];
  x?: number;
  y?: number;
}
