package me.ryleykimmel.brandywine.parser.impl;

import java.io.IOException;
import java.io.Reader;

import org.sql2o.Sql2o;

import me.ryleykimmel.brandywine.ServerContext;
import me.ryleykimmel.brandywine.fs.FileSystem;
import me.ryleykimmel.brandywine.parser.TomlParser;

import com.moandjiezana.toml.Toml;

/**
 * Parses a configuration for this Server.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class ConfigurationParser extends TomlParser {

	/**
	 * The context of the Server.
	 */
	private final ServerContext context;

	/**
	 * Constructs a new {@link ConfigurationParser} with the specified path and ServerContext.
	 *
	 * @param path The path to the source.
	 * @param context The context of the Server.
	 */
	public ConfigurationParser(String path, ServerContext context) {
		super(path);
		this.context = context;
	}

	@Override
	public void parse(Reader source, Toml data) throws IOException {
		context.setName(data.getString("name"));
		context.setGamePort(getInteger(data, "game_port"));
		context.setFileSystem(FileSystem.create(data.getString("fs_directory")));
		context.setConnectionLimit(getInteger(data, "connection_limit"));
		context.setSql2o(new Sql2o(context.getDatabaseAddress(), context.getDatabaseUsername(), context.getDatabasePassword()));
	}

}