<script lang="ts">
	import Header from '$lib/components/molecules/Header.svelte';
	import { authService } from '$lib/shared/stores/auth';
	import { get } from 'svelte/store';

	let { children } = $props();
</script>

<Header type={get(authService.isAdmin) ? 'admin' : 'user'}/>
{@render children?.()}
