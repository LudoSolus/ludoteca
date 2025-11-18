import { jwtDecode } from "jwt-decode";
import { UserRole } from "../enums/user-role.enum";

export interface JwtAuthData{
    name: string
    id: string
    role: UserRole
    publicId: string
    email: string
    iat: Date
    exp: Date
}

export function decodeAuthJwt(token: string | null): JwtAuthData | undefined{
    if(!token) return;

    const decoded = jwtDecode(token) as any
    if(!decoded) return;
    
    return {
        name: decoded.name || "",
        id: decoded.sub || "",
        role: decoded.role || UserRole.USER,
        publicId: decoded.publicId || "",
        email: decoded.email || "",
        iat: new Date((decoded.iat || 0) * 1000),
        exp: new Date((decoded.exp || 0) * 1000)
    }
}       