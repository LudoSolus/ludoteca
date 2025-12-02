export function stringIsValid(value: string | undefined | null): boolean {
	if (!value) return false;

	value = value.trim();
	return !!value && value.length > 0 && value != 'N/A';
}
