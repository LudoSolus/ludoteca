<script lang="ts">
	import Header from '$lib/components/molecules/Header.svelte';
	import { authService } from '$lib/shared/stores/auth';

	let { children } = $props();
</script>

<Header type={authService.isAdmin() ? 'admin' : 'user'}/>
{@render children?.()}
