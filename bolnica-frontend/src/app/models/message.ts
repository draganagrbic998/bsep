export interface Message{
    id: number;
    date: Date;
    pulse: number;
    pressure: number;
    temperature: number;
    oxygenLevel: number;
    patient: string;
    insuredNumber: string;
}
