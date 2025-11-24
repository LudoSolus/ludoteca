import { PUBLIC_API_URL } from '$env/static/public';
import { EErrorCode } from '$lib/shared/enums/error-code.enum';
import type { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
import type { IQueryResult } from '$lib/shared/handlers/query/query-result.interface';
import type { IQuery } from '$lib/shared/handlers/query/query.interface';
import type { AxiosResponse } from 'axios';
import { Observable, from, map } from 'rxjs';
import type { IBoardGame } from './list-board-games.interface';
import { ECategory } from '$lib/shared/enums/category.enum';

export class ListBoardGamesQuery implements IQuery<IBoardGame[]> {
	constructor() {}

	execute(queriesHandler: QueriesHandlerService): Observable<IQueryResult<IBoardGame[]>> {
		const url = `${PUBLIC_API_URL}/queries/admin/games/list-all-games`;
		return from(queriesHandler.axios.get<IQueryResult<IBoardGame[]>>(url)).pipe(
			map((result: AxiosResponse<IQueryResult<IBoardGame[]>>) => {
				if (!result || !result.data || !result.data.resultData)
					return { resultData: [], errorCode: EErrorCode.none } as IQueryResult<IBoardGame[]>;

				result.data.resultData = result.data.resultData.map((game) => ({
					...game,
					category: ECategory[game.category as unknown as keyof typeof ECategory]
				}));
				return result.data;
			})
		);
	}
}
