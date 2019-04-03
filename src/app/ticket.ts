export interface Ticket {
    id: number;
    description: string;
    submittedOn: Date;
    
    priorityID: number;
    priorityName: string;
    
    stateID: number;
    stateName: string;

    userID: number;
    lastName: string;
    firstName: string;
    userName: string;
}
