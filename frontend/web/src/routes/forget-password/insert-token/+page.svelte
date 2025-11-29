<script lang="ts">
	import { goto } from '$app/navigation';
	import InsertToken from '$lib/components/templates/InsertToken.svelte';

	function goToChangePasswordPage(token: string) {
		goto(`/forget-password/change-password?token=${token}`);
	}
</script>

<InsertToken onInsertToken={goToChangePasswordPage} />
