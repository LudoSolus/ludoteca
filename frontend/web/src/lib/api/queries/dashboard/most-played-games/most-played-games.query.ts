import { PUBLIC_API_URL } from '$env/static/public';
import { EErrorCode } from '$lib/shared/enums/error-code.enum';
import type { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
import type { IQueryResult } from '$lib/shared/handlers/query/query-result.interface';
import type { IQuery } from '$lib/shared/handlers/query/query.interface';
import type { AxiosResponse } from 'axios';
import { Observable, from, map } from 'rxjs';
import type { IMostPlayedGamesResponse } from './most-played-games.interface';

export class MostPlayedGamesQuery implements IQuery<IMostPlayedGamesResponse> {
	constructor() {}

	execute(
		queriesHandler: QueriesHandlerService
	): Observable<IQueryResult<IMostPlayedGamesResponse>> {
		const url = `${PUBLIC_API_URL}/queries/admin/dashboard/most-played-games`;
		return from(queriesHandler.axios.get<IQueryResult<IMostPlayedGamesResponse>>(url)).pipe(
			map((result: AxiosResponse<IQueryResult<IMostPlayedGamesResponse>>) => {
				if (!result || !result.data || !result.data.resultData)
					return {
						resultData: { data: [] },
						errorCode: EErrorCode.none
					} as unknown as IQueryResult<IMostPlayedGamesResponse>;

				return result.data;
			})
		);
	}
}
