
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

export interface Book {

    id: Number,
    guestName: String,
    guestID: Number,
    guestEmail: String,
    price: Number,
    units: Number,
    numGuest: Number,
    status: Status,
    dateIn: String,
    dateOut: String,
    createdAt: String,
    updatedAt: String

}

export enum Status {

    PENDING,
    CONFIRMED,
    CANCELED

}