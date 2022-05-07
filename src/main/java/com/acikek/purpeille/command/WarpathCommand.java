package com.acikek.purpeille.command;

import com.acikek.purpeille.warpath.Aspects;
import com.acikek.purpeille.warpath.Revelations;
import com.acikek.purpeille.warpath.Warpath;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.quiltmc.qsl.command.api.EnumArgumentType;

public class WarpathCommand {

    public static final String NAME = "warpath";

    public static final DynamicCommandExceptionType INVALID_STACK = getException("invalid.stack");

    public static final String ADD_SUCCESS = "success.add";
    public static final String REMOVE_SUCCESS = "success.remove";

    public static int add(CommandContext<ServerCommandSource> context, boolean hasAspect) throws CommandSyntaxException {
        Revelations revelation = EnumArgumentType.getEnumConstant(context, "revelation", Revelations.class);
        Aspects aspect = hasAspect ? EnumArgumentType.getEnumConstant(context, "aspect", Aspects.class) : null;
        ItemStack stack = getStack(context);
        Warpath.remove(stack);
        Warpath.add(stack, revelation.value, hasAspect ? aspect.value : null);
        context.getSource().sendFeedback(getMessage(ADD_SUCCESS, Warpath.getWarpath(revelation, aspect, false)), false);
        return 0;
    }

    public static int remove(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ItemStack stack = getStack(context);
        Text warpath = Warpath.getWarpath(stack, false);
        Warpath.remove(stack);
        context.getSource().sendFeedback(getMessage(REMOVE_SUCCESS, warpath), false);
        return 0;
    }

    public static ItemStack getStack(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        LivingEntity entity = (LivingEntity) EntityArgumentType.getEntity(context, "targets");
        ItemStack stack = entity.getMainHandStack();
        if (stack.isEmpty()) {
            throw INVALID_STACK.create(entity.getName().getString());
        }
        return stack;
    }

    public static MutableText getMessage(String key, Object value) {
        return new TranslatableText("command.purpeille.warpath." + key, value);
    }

    public static DynamicCommandExceptionType getException(String key) {
        return new DynamicCommandExceptionType(value -> getMessage(key, value));
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal(NAME)
                .then(CommandManager.argument("targets", EntityArgumentType.entities())
                        .then(CommandManager.literal("add")
                                .then(CommandManager.argument("revelation", EnumArgumentType.enumConstant(Revelations.class))
                                        .then(CommandManager.argument("aspect", EnumArgumentType.enumConstant(Aspects.class))
                                                .executes(context -> WarpathCommand.add(context, true)))
                                        .executes(context -> WarpathCommand.add(context, false))
                                ))
                        .then(CommandManager.literal("remove")
                                .executes(WarpathCommand::remove))));
    }
}
