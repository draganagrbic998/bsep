export class LogConfiguration {
  id = 0;
  path = 'path';
  interval = 0;
  regExp = '.*';
}

export class Configuration {
  configurations: LogConfiguration[];
}
