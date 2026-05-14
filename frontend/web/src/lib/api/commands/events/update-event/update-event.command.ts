import { PUBLIC_API_URL } from "$env/static/public";
import type { ICommandResult } from "$lib/shared/handlers/command/command-result.interface";
import type { ICommand } from "$lib/shared/handlers/command/command.interface";
import type { CommandsHandlerService } from "$lib/shared/handlers/command/commands-handler.service";
import type { AxiosResponse } from "axios";
import { Observable, from } from "rxjs";
import type { IUpdateEventRequest } from "./update-event.interface";

export class UpdateEventCommand implements ICommand {
    constructor(
        private eventId: string,
        private body: IUpdateEventRequest
    ) { }

    execute(handler: CommandsHandlerService): Observable<AxiosResponse<ICommandResult>> {
        const url = `${PUBLIC_API_URL}/commands/admin/events/${this.eventId}/update`;
        return from(handler.axios.put<ICommandResult>(url, this.body));
    }
}