import {Role} from "./user";

export interface SignUpRequest {
    username?: string;
    email?: string;
    password?: string;
    role: Role[];
}
