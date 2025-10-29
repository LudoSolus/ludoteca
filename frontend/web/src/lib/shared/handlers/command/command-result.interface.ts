import type { EErrorCode } from "$lib/shared/enums/error-code.enum";

export interface ICommandResult<T = any> {
  rows: number;
  resultData: T;
  errorCode: EErrorCode;
  errorName: string;
  errorMessage: string;
}
