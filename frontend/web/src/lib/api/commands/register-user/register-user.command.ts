import { PUBLIC_API_URL } from "$env/static/public";
import { EErrorCode } from "$lib/shared/enums/error-code.enum";
import type { UserRole } from "$lib/shared/enums/user-role.enum";
import type { ICommandResult } from "$lib/shared/handlers/command/command-result.interface";
import type { ICommand } from "$lib/shared/handlers/command/command.interface";
import type { CommandsHandlerService } from "$lib/shared/handlers/command/commands-handler.service";
import type { AxiosResponse } from "axios";
import { from, type Observable } from "rxjs";

export class RegisterUserCommand implements ICommand {

    constructor(
            private name: string,
            private cpf: string,
            private email: string,
            private password: string,
            private phone: string,
            private ra: string | null,
            private birthDate: Date,
            private userRole: UserRole,
            private institutionId: string,
    ) { }

    execute(handler: CommandsHandlerService): Observable<AxiosResponse<ICommandResult>> {
        const url = `${PUBLIC_API_URL}/commands/users/register`;
        return from(handler.axios.post<ICommandResult>(url, this));
    }
}