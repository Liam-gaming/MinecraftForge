/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.minecraftforge.event.entity.living;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This Event and its subevents gets fired from  {@link LivingEntity} on the  {@link MinecraftForge#EVENT_BUS}.<br>
 */
public class PotionEvent extends LivingEvent
{
    @Nullable
    protected final MobEffectInstance effect;

    public PotionEvent(LivingEntity living, MobEffectInstance effect)
    {
        super(living);
        this.effect = effect;
    }
    /**
     * Retuns the PotionEffect.
     */
    @Nullable
    public MobEffectInstance getPotionEffect()
    {
        return effect;
    }

    /**
     * This Event is fired when a Potion is about to get removed from an Entity.
     * This Event is {@link Cancelable}.
     * This Event does not have a result.
     */
    @Cancelable
    public static class PotionRemoveEvent extends PotionEvent
    {
        private final MobEffect potion;

        public PotionRemoveEvent(LivingEntity living, MobEffect potion)
        {
            super(living, living.getEffect(potion));
            this.potion = potion;
        }

        public PotionRemoveEvent(LivingEntity living, MobEffectInstance effect)
        {
            super(living, effect);
            this.potion = effect.getEffect();
        }

        /**
         * @return the Potion which is tried to remove from the Entity.
         */
        public MobEffect getPotion()
        {
            return this.potion;
        }

        /**
         * @return the PotionEffect. In the remove event this can be null if the Entity does not have a {@link MobEffect} of the right type active.
         */
        @Override
        @Nullable
        public MobEffectInstance getPotionEffect()
        {
            return super.getPotionEffect();
        }
    }

    /**
     * This Event is fired to check if a Potion can get applied to an Entity.
     * This Event is not {@link Cancelable}
     * This Event has a result {@link HasResult}.
     * ALLOW will apply this potion effect.
     * DENY will not apply this potion effect.
     * DEFAULT will run vanilla logic to determine if this potion isApplicable.
     */
    @HasResult
    public static class PotionApplicableEvent extends PotionEvent
    {
        public PotionApplicableEvent(LivingEntity living, MobEffectInstance effect)
        {
            super(living, effect);
        }

        /**
         * @return the PotionEffect.
         */
        @Override
        @NotNull
        public MobEffectInstance getPotionEffect()
        {
            return super.getPotionEffect();
        }
    }

    /**
     * This Event is fired when a new Potion is added to the Entity. This is also fired if the Entity already has this effect but with different duration/level.
     * This Event is not {@link Cancelable}
     * This Event does not have a Result.
     */
    public static class PotionAddedEvent extends PotionEvent
    {
        private final MobEffectInstance oldEffect;
        private final Entity source;

        public PotionAddedEvent(LivingEntity living, MobEffectInstance oldEffect, MobEffectInstance newEffect, Entity source)
        {
            super(living, newEffect);
            this.oldEffect = oldEffect;
            this.source = source;
        }

        /**
         * @return the added PotionEffect. This is the umerged PotionEffect if the old PotionEffect is not null.
         */
        @Override
        @NotNull
        public MobEffectInstance getPotionEffect()
        {
            return super.getPotionEffect();
        }

        /**
         * @return the old PotionEffect. THis can be null if the entity did not have an effect of this kind before.
         */
        @Nullable
        public MobEffectInstance getOldPotionEffect()
        {
            return oldEffect;
        }

        /**
         * Returns the entity source of the effect, or {@code null} if none exists.
         *
         * @return the entity source of the effect, or {@code null}
         */
        @Nullable
        public Entity getPotionSource()
        {
            return source;
        }
    }

    /**
     * This Event is fired when a Potion effect expires on an Entity.
     * This Event is not {@link Cancelable}
     * This Event does not have a Result.
     */
    public static class PotionExpiryEvent extends PotionEvent
    {
        public PotionExpiryEvent(LivingEntity living, MobEffectInstance effect)
        {
            super(living, effect);
        }
    }
}
