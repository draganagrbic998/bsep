import { Component } from '@angular/core';
import { ConfigurationService } from '../../../core/services/configuration.service';
import { FormControl } from '@angular/forms';
import { Configuration, LogConfiguration } from '../../../core/model/configuration';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.scss']
})
export class ConfigurationComponent {

  configuration: Configuration;
  oldConfiguration: { [s: number]: LogConfiguration; } = {};
  loading = false;
  hospitalApiControl: FormControl = new FormControl('');
  currentId = 1;

  constructor(
    private configurationService: ConfigurationService,
    private messageService: MessageService
  ) { }

  get connected(): boolean {
    return !!this.configuration;
  }

  connect(): void {
    this.loading = true;
    // tslint:disable-next-line: deprecation
    this.configurationService.connect(this.hospitalApiControl.value.trim()).subscribe((response: Configuration) => {
      this.loading = false;
      this.currentId = 1;
      this.configuration = response;
      this.configuration.configurations = this.configuration.configurations.map(lc => {
        lc.id = this.currentId++;
        return lc;
      });
      if (this.configuration){
        this.hospitalApiControl.disable();
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'Connection established.'});
      }
      else{
        this.hospitalApiControl.enable();
        this.messageService.add({severity: 'error', summary: 'Error', detail: 'Connection not established.'});
      }
    });
  }

  save(): void {
    if (this.checkConfiguration()) {
      this.loading = true;
      // tslint:disable-next-line: deprecation
      this.configurationService.save(this.hospitalApiControl.value.trim(), this.configuration).subscribe((response: boolean) => {
        this.loading = false;
        this.configuration = null;
        this.hospitalApiControl.enable();
        if (response){
          this.messageService.add({severity: 'success', summary: 'Success', detail: 'Configuration saved'});
        }
        else{
          this.messageService.add({severity: 'error', summary: 'Error', detail: 'Configuration not saved'});
        }
      });
    }
  }

  cancel(): void {
    this.configuration = null;
    this.currentId = 1;
    this.hospitalApiControl.enable();
  }

  addConfiguration(): void {
    const lcfg = new LogConfiguration();
    lcfg.id = this.currentId++;
    this.configuration.configurations.push(lcfg);
  }

  deleteConfiguration(index: number): void {
    this.configuration.configurations.splice(index, 1);
  }

  onRowEditInit(logConfiguration: LogConfiguration): void {
    this.oldConfiguration[logConfiguration.id] = logConfiguration;
  }

  onRowEditSave(configuration: LogConfiguration): void {
    if (!this.isValid(configuration)) {
      return;
    }
    delete this.oldConfiguration[configuration.id];
    this.messageService.add({severity: 'success', summary: 'Success', detail: 'Configuration modified'});
  }

  onRowEditCancel(logConfiguration: LogConfiguration, index: number): void {
    this.configuration.configurations[index] = this.oldConfiguration[logConfiguration.id];
    delete this.oldConfiguration[logConfiguration.id];
  }

  private checkConfiguration(): boolean {
    return this.configuration.configurations.every(conf => this.isValid(conf));
  }

  private isValid(configuration: LogConfiguration): boolean {
    if (configuration.path.length < 1) {
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Path invalid'});
      return false;
    }
    if (configuration.interval < 1000) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'Interval has to be larger than 1000 ms'
      });
      return false;
    }

    try {
      // tslint:disable-next-line:no-unused-expression
      new RegExp(configuration.regExp);
    }
    catch {
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Regular expression invalid'});
      return false;
    }

    return true;
  }

}
