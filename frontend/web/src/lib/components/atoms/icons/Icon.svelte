<script lang="ts">
    import Fa, { type IconSize } from "svelte-fa";
    import type { IconDefinition } from '@fortawesome/free-solid-svg-icons';
    import type { SvelteComponent } from 'svelte';
    
    // Props
    export let icon: IconDefinition | typeof SvelteComponent | string;
    export let color: string = 'currentColor';
    export let size: IconSize | number = 1;
    export let classes: string = '';

    $: dataUri = ((): string => {
        if (typeof icon === 'string') {
            if (icon.includes('<svg')) {
                let newIconData = encodeURIComponent(icon.replace(/fill=['"](?!url\()([^'"]*)['"]/gi, `fill="${color}"`));
                return `data:image/svg+xml;utf8,${newIconData}`
            }
            return icon;
        }
        return '';
    })();
</script>

{#if typeof icon === 'function'}
    <svelte:component 
        this={icon}
        {color}
        size = {`${(size as number)*17}`.concat('px')}
        classes={classes}
    />
{:else if typeof icon === 'string'}
    <img src={dataUri} alt={"Icon ".concat(icon)} class={classes}
    width={`${(size as number)*17}`.concat('px')} height={`${(size as number)*17}`.concat('px')}>
{:else}
    <Fa 
        icon={icon}
        {color} 
        size={size.toString().concat('x') as IconSize} 
        class={classes}
    />
{/if}