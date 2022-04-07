package com.acikek.purpeille.warpath.component;

import com.acikek.purpeille.warpath.Synergy;
import com.acikek.purpeille.warpath.Tone;
import com.acikek.purpeille.warpath.Type;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class Revelation extends Aspect {

    public EntityAttribute attribute;
    public Item item;
    public EntityAttributeModifier.Operation operation;

    public Revelation(String name, Tone tone, int index, double modifier, EntityAttribute attribute, Item item, boolean multiply) {
        super(name, tone, index, modifier);
        this.attribute = attribute;
        this.item = item;
        operation = multiply ? EntityAttributeModifier.Operation.MULTIPLY_TOTAL : EntityAttributeModifier.Operation.ADDITION;
    }

    @Override
    public Type getType() {
        return Type.REVELATION;
    }

    public Text getRite() {
        return new TranslatableText("rite.purpeille." + name).styled(style -> style.withColor(13421772));
    }

    public double getModifier(ItemStack stack, Aspect aspect) {
        double value = stack.isOf(item) ? modifier * 1.2 : modifier;
        if (aspect == null) {
            return value;
        }
        return value * aspect.modifier * Synergy.getSynergy(this, aspect).modifier;
    }
}
