import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-empty-container',
  templateUrl: './empty-container.component.html',
  styleUrls: ['./empty-container.component.scss']
})
export class EmptyContainerComponent implements OnInit {

  constructor() { }

  @Input() title = 'NO ITEMS';

  ngOnInit(): void {
  }

}
