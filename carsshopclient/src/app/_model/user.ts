import { Product } from "./product";

export class User {
    id?:number;
    username?: string;
    password?: string;
    contactName?: string;
    email?: string;
    phoneNumber?: string;
    address?: string;
    authority?: string[];
    products?: Product[];
}
