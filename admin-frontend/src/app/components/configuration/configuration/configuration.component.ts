import { Component, OnInit } from '@angular/core';
import {ConfigurationService} from '../../../core/services/configuration.service';
import {FormControl} from '@angular/forms';
import {Configuration} from '../../../core/model/configuration';

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.scss']
})
export class ConfigurationComponent implements OnInit {

  hospitalApiControl: FormControl = new FormControl();

  constructor(private configurationService: ConfigurationService) { }

  ngOnInit(): void {
  }

  connect(): void {
    this.configurationService.connect(this.hospitalApiControl.value).subscribe((val: Configuration) => {
      this.configurationService.configuration.next(val);
    });
  }

  get connected(): boolean {
    return !!this.configurationService.configuration.getValue();
  }

}
