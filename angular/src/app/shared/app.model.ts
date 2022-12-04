export interface User {
    id: Number,
    createdAt: String,
    email: String,
    enabled: Boolean,
    firstName: String,
    lastName: String,
    name: String,
    password: String,
    role: Role,
    updatedAt: String

}

export interface Datos {

    email: String,
    password: String
}

export enum Role {
    HOST,
    GUEST
}