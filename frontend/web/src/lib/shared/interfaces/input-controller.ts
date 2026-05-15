export interface IInputController {
	value: any;
	error: string | null;
	touched: boolean;
	required: boolean;
}


export type IFormController<T extends string> = {
	[key in T]: IInputController;
};

export function inputHasValue(value: string): boolean {
	return (!!value && value.length > 0);
}
