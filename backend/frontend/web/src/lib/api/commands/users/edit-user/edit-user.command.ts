import { PUBLIC_API_URL } from "$env/static/public";
import type { ICommandResult } from "$lib/shared/handlers/command/command-result.interface";
import type { ICommand } from "$lib/shared/handlers/command/command.interface";
import type { CommandsHandlerService } from "$lib/shared/handlers/command/commands-handler.service";
import type { IEditUserRequest } from "./edit-user.interface";
import type { AxiosResponse } from "axios";
import { from, type Observable } from "rxjs";

export class EditUserCommand implements ICommand {
    constructor(private boardGameId: string, private body: IEditUserRequest) {}

    execute(handler: CommandsHandlerService): Observable<AxiosResponse<ICommandResult>> {
        const url = `${PUBLIC_API_URL}/commands/admin/users/${this.boardGameId}/change-role`;
        return from(handler.axios.post<ICommandResult>(url, this.body));
    }
}