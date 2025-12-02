import { PUBLIC_API_URL } from "$env/static/public";
import type { ICommandResult } from "$lib/shared/handlers/command/command-result.interface";
import type { ICommand } from "$lib/shared/handlers/command/command.interface";
import type { CommandsHandlerService } from "$lib/shared/handlers/command/commands-handler.service";
import type { AxiosResponse } from "axios";
import { Observable, from } from "rxjs";

export class LoginUserCommand implements ICommand{
        constructor(
                private email: string,
                private password: string,
        ) { }
    
        execute(handler: CommandsHandlerService): Observable<AxiosResponse<ICommandResult>> {
            const url = `${PUBLIC_API_URL}/commands/users/login`;
            return from(handler.axios.post<ICommandResult>(url, this));
        }
}