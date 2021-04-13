import { Component, OnInit } from '@angular/core';
import {ConfigurationService} from '../../../core/services/configuration.service';
import {FormControl} from '@angular/forms';
import {Configuration, LogConfiguration} from '../../../core/model/configuration';
import {InputText} from 'primeng/inputtext';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.scss']
})
export class ConfigurationComponent implements OnInit {

  hospitalApiControl: FormControl = new FormControl();
  oldConfig: { [s: number]: LogConfiguration; } = {};
  loading = false;

  constructor(private configurationService: ConfigurationService,
              private messageService: MessageService) { }

  ngOnInit(): void {
  }

  toggleConnection(): void {
    this.loading = true;
    if (this.connected) {
      if (this.checkConfiguration()) {
        this.configurationService.save(this.hospitalApiControl.value, this.configuration).subscribe(() => {
          this.messageService.add({severity: 'success', summary: 'Success', detail: 'Configuration successfully saved'});
          this.configurationService.configuration.next(null);
          this.hospitalApiControl.enable();
          this.loading = false;
        }, () => {
          this.messageService.add({severity: 'error', summary: 'Error', detail: 'Configuration wasn\'t saved'});
          this.configurationService.configuration.next(null);
          this.hospitalApiControl.enable();
          this.loading = false;
        });
      }

      return;
    }
    this.configurationService.connect(this.hospitalApiControl.value).subscribe((val: Configuration) => {
      this.configurationService.configuration.next(val);
      this.messageService.add({severity: 'success', summary: 'Success', detail: 'Connection successfully established.'});
      this.hospitalApiControl.disable();
      this.loading = false;
    }, () => {
      this.configurationService.configuration.next(null);
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Connection not established.'});
      this.hospitalApiControl.enable();
      this.loading = false;
    });
  }

  checkConfiguration(): boolean {
    return this.configuration.configurations.every(c => this.isValid(c));
  }

  isValid(logConfiguration: LogConfiguration): boolean {
    if (logConfiguration.path.length < 1) {
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Path is invalid'});
      return false;
    }
    if (logConfiguration.interval < 1000) {
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Interval has to be larger than 1000'});
      return false;
    }

    try {
      // tslint:disable-next-line:no-unused-expression
      new RegExp(logConfiguration.regExp);
    } catch (e) {
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Regular expression is not valid'});
      return false;
    }

    return true;
  }

  addNew(): void {
    this.configuration.configurations.push(new LogConfiguration());
  }

  onRowEditInit(logConfiguration: LogConfiguration, index: number): void {
    this.oldConfig[index] = logConfiguration;
  }

  onRowEditSave(tableRowElement: HTMLTableRowElement, logConfiguration: LogConfiguration, index: number): void {
    if (!this.isValid(logConfiguration)) {
      return;
    }

    this.messageService.add({severity: 'success', summary: 'Success', detail: 'Configuration successfully modified'});
    delete this.oldConfig[index];
  }

  onRowEditCancel(product: LogConfiguration, index: number): void {
    this.configuration.configurations[index] = this.oldConfig[index];
    delete this.oldConfig[index];
  }

  deleteConfiguration(index: number): void {
    this.configuration.configurations.splice(index, 1);
  }


  get configuration(): Configuration {
    return this.configurationService.configuration.getValue();
  }

  get connected(): boolean {
    return !!this.configurationService.configuration.getValue();
  }

}
