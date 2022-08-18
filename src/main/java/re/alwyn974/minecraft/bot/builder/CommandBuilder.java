package re.alwyn974.minecraft.bot.builder;

import re.alwyn974.minecraft.bot.cmd.utils.ICommand;
import re.alwyn974.minecraft.bot.cmd.utils.IExecutor;

/**
 * The command builder, useful to create simple command without creating a class
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.6
 * @since 1.0.6
 */
public class CommandBuilder {

    private String name;
    private String description;
    private String usage;
    private boolean needToBeConnected = false;
    private IExecutor executor;

    /**
     * Add a name to the Command
     *
     * @param name the name of the command
     * @return the builder
     */
    public CommandBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Add a description to the Command
     *
     * @param description the description of the command
     * @return the builder
     */
    public CommandBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Add an usage to the Command
     *
     * @param usage the usage of the command
     * @return the builder
     */
    public CommandBuilder withUsage(String usage) {
        this.usage = usage;
        return this;
    }

    /**
     * Specify if the command can only be used on a server
     *
     * @param needToBeConnected if the command can only be used on a server
     * @return the builder
     */
    public CommandBuilder withNeedToBeConnected(boolean needToBeConnected) {
        this.needToBeConnected = needToBeConnected;
        return this;
    }

    /**
     * Add the executor of the command
     *
     * @param executor the executor {@link IExecutor}
     * @return the builder
     */
    public CommandBuilder withExecutor(IExecutor executor) {
        this.executor = executor;
        return this;
    }

    /**
     * Build the command
     *
     * @return an {@link ICommand} object
     * @throws CommandBuilderException if the command doesn't match the requirement
     * @see #checkValidity()
     */
    public ICommand build() throws CommandBuilderException {
        checkValidity();
        return new ICommand() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getDescription() {
                return description;
            }

            @Override
            public String getUsage() {
                return usage;
            }

            @Override
            public boolean needToBeConnected() {
                return needToBeConnected;
            }

            @Override
            public IExecutor executor() {
                return executor;
            }
        };
    }

    /**
     * Check the validity of the command <br>
     * <ul>
     * <li>name can't be null</li>
     * <li>usage can't be null</li>
     * <li>description can't be null</li>
     * <li>executor can't be null</li>
     * </ul>
     *
     * @throws CommandBuilderException if the command doesn't match the requirement
     */
    private void checkValidity() throws CommandBuilderException {
        if (name == null)
            throw new CommandBuilderException("name == null");
        if (usage == null)
            throw new CommandBuilderException("usage == null");
        if (description == null)
            throw new CommandBuilderException("description == null");
        if (executor == null)
            throw new CommandBuilderException("executor == null");
    }

}
