export interface User {
    id?: string;
    username?: string;
    email: string;
    password: string;
    parentId: string;
    token?: string;
    userRole: Role[];
    isActive: boolean;
  }
  
  export enum Role {
    USER = "user",
    ADMIN = "admin",
    CONTENT_MAKER = "content maker"
  }