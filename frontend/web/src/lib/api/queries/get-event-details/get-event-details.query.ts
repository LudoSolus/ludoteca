import { PUBLIC_API_URL } from '$env/static/public';
import { EErrorCode } from '$lib/shared/enums/error-code.enum';
import type { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
import type { IQueryResult } from '$lib/shared/handlers/query/query-result.interface';
import type { IQuery } from '$lib/shared/handlers/query/query.interface';
import type { AxiosResponse } from 'axios';
import { Observable, from, map } from 'rxjs';
import type { GetEventDetailsResponse } from './get-event-details.interface';

export class GetEventDetailsQuery implements IQuery<GetEventDetailsResponse> {
	constructor(private eventId: string) {}

	execute(
		queriesHandler: QueriesHandlerService
	): Observable<IQueryResult<GetEventDetailsResponse>> {
		const url = `${PUBLIC_API_URL}/queries/events/${this.eventId}/details`;
		return from(queriesHandler.axios.get<IQueryResult<GetEventDetailsResponse>>(url)).pipe(
			map((result: AxiosResponse<IQueryResult<GetEventDetailsResponse>>) => {
				if (!result || !result.data || !result.data.resultData)
					return { resultData: {}, errorCode: EErrorCode.none } as IQueryResult<GetEventDetailsResponse>;
				return result.data;
			})
		);
	}
}
