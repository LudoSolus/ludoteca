import { PUBLIC_API_URL } from '$env/static/public';
import type { ICommandResult } from '$lib/shared/handlers/command/command-result.interface';
import type { ICommand } from '$lib/shared/handlers/command/command.interface';
import type { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
import type { AxiosResponse } from 'axios';
import { Observable, from } from 'rxjs';
import type { IEditBoardGameRequest } from './edit-board-game.interface';

export class EditBoardGameCommand implements ICommand {
	constructor(
		private boardGameId: string,
		private body: IEditBoardGameRequest
	) {}

	execute(handler: CommandsHandlerService): Observable<AxiosResponse<ICommandResult>> {
		const url = `${PUBLIC_API_URL}/commands/admin/games/${this.boardGameId}/update`;
		return from(handler.axios.put<ICommandResult>(url, this.body));
	}
}
