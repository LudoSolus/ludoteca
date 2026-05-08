import { PUBLIC_API_URL } from '$env/static/public';
import type { ICommandResult } from '$lib/shared/handlers/command/command-result.interface';
import type { ICommand } from '$lib/shared/handlers/command/command.interface';
import type { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
import type { AxiosResponse } from 'axios';
import { Observable, from } from 'rxjs';
import type { IRegisterEventRequest } from './register-event.interface';

export class RegisterEventCommand implements ICommand {
    constructor(private body: IRegisterEventRequest) { }

    execute(handler: CommandsHandlerService): Observable<AxiosResponse<ICommandResult>> {
        const url = `${PUBLIC_API_URL}/commands/admin/events/register`;
        return from(handler.axios.post<ICommandResult>(url, this.body));
    }
}