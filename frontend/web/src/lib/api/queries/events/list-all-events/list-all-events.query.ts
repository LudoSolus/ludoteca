import { PUBLIC_API_URL } from "$env/static/public";
import type { ECategory } from "$lib/shared/enums/category.enum";
import { EErrorCode } from "$lib/shared/enums/error-code.enum";
import type { QueriesHandlerService } from "$lib/shared/handlers/query/queries-handler.service";
import type { IQueryResult } from "$lib/shared/handlers/query/query-result.interface";
import type { IQuery } from "$lib/shared/handlers/query/query.interface";
import type { AxiosResponse } from "axios";
import { Observable, from, map } from "rxjs";
import type { IListEvent } from "./list-all-events.interface";

export class ListAllEventsQuery implements IQuery<IListEvent[]> {
    constructor() {}

    execute(queriesHandler: QueriesHandlerService): Observable<IQueryResult<IListEvent[]>> {
        const url = `${PUBLIC_API_URL}/queries/admin/events/list-all-events`;
        return from(queriesHandler.axios.get<IQueryResult<IListEvent[]>>(url)).pipe(
            map((result: AxiosResponse<IQueryResult<IListEvent[]>>) => {
                if (!result || !result.data || !result.data.resultData)
                    return { resultData: [], errorCode: EErrorCode.none } as IQueryResult<IListEvent[]>;

                result.data.resultData = result.data.resultData.map((event) => ({
                    ...event,
                    startDate: new Date(event.startDate),
                    finalDate: new Date(event.finalDate),
                }));
                return result.data;
            })
        );
    }
}