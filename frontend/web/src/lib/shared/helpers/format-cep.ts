export function formatCEP(value: string | number): string {
	const digits = String(value).replace(/\D/g, '');
	if (digits.length !== 8) return value.toString();

	return digits.replace(/(\d{5})(\d{3})/, '$1-$2');
}
