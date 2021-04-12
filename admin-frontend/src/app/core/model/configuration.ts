class LogConfiguration {
  path: string;
  interval: number;
  regExp: string;
}

export class Configuration {
  configurations: LogConfiguration[];
}
