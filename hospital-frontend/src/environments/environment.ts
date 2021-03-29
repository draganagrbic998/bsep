// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  authApi: 'https://localhost:8081/auth',
  patientsApi: 'https://localhost:8081/api/patients',
  messagesApi: 'https://localhost:8081/api/messages',
  logsApi: 'https://localhost:8081/api/logs',
  alarmsApi: 'https://localhost:8081/api/alarms',
  alarmTriggeringsApi: 'https://localhost:8081/api/alarm-triggerings',
  reportApi: 'https://localhost:8081/api/report',

  loginRoute: 'login',
  patientFormRoute: 'patient-form',
  patientListRoute: 'patient-list',
  patientDetailsRoute: 'patient-details',
  messageListRoute: 'message-list',
  logListRoute: 'log-list',
  alarmTriggeringListRoute: 'alarm-triggering-list',
  reportRoute: 'report'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
