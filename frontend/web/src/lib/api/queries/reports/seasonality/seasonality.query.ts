import { PUBLIC_API_URL } from '$env/static/public';
import { EErrorCode } from '$lib/shared/enums/error-code.enum';
import type { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
import type { IQueryResult } from '$lib/shared/handlers/query/query-result.interface';
import type { IQuery } from '$lib/shared/handlers/query/query.interface';
import type { AxiosResponse } from 'axios';
import { Observable, from, map } from 'rxjs';
import type { ISeasonalityResponse } from './seasonality.interface';

export class SeasonalityQuery implements IQuery<ISeasonalityResponse> {
	constructor() {}

	execute(
		queriesHandler: QueriesHandlerService
	): Observable<IQueryResult<ISeasonalityResponse>> {
		const url = `${PUBLIC_API_URL}/queries/admin/reports/seasonality`;
		return from(queriesHandler.axios.get<IQueryResult<ISeasonalityResponse>>(url)).pipe(
			map((result: AxiosResponse<IQueryResult<ISeasonalityResponse>>) => {
				if (!result || !result.data || !result.data.resultData)
					return {
						resultData: [],
						errorCode: EErrorCode.none
					} as unknown as IQueryResult<ISeasonalityResponse>;

				return result.data;
			})
		);
	}
}
