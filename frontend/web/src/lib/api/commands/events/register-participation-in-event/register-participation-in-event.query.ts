import { PUBLIC_API_URL } from '$env/static/public';
import type { ICommandResult } from '$lib/shared/handlers/command/command-result.interface';
import type { ICommand } from '$lib/shared/handlers/command/command.interface';
import type { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
import type { AxiosResponse } from 'axios';
import { Observable, from } from 'rxjs';

export class RegisterParticipationInEventCommand implements ICommand {
	constructor(
		private userPublicId: string,
		private eventId: string
	) {}

	execute(handler: CommandsHandlerService): Observable<AxiosResponse<ICommandResult>> {
		const url = `${PUBLIC_API_URL}/command/admin/participations-event/register-participation-event`;
		return from(handler.axios.post<ICommandResult>(url, this));
	}
}
