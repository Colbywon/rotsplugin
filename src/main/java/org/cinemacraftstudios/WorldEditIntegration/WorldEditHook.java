package org.cinemacraftstudios.WorldEditIntegration;

import com.sk89q.worldedit.EditSession.Stage;

import java.util.UUID;

import org.cinemacraftstudios.PlayerStats.PlayerStatsManager;
import org.cinemacraftstudios.PlayerStats.Session;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.event.extent.EditSessionEvent;
import com.sk89q.worldedit.extent.AbstractDelegateExtent;
import com.sk89q.worldedit.util.eventbus.Subscribe;

/**
 * Interfaces with worldedit
 * 
 * @author Simon
 *
 */
public class WorldEditHook {

	@Subscribe
	public void wrapForBlockCounting(EditSessionEvent event) {

		event.setExtent(new AbstractDelegateExtent(event.getExtent()) {

			UUID uuid = event.getActor().getUniqueId();
			Session session = PlayerStatsManager.getInstance().GetSession(uuid);

			@Override
			/**
			 * Gets Called for every block that if affected by a worldedit command
			 */
			public boolean setBlock(Vector location, BaseBlock block) throws WorldEditException {
				if (event.getStage() == Stage.BEFORE_CHANGE) {
					session.blocksChangedWorldEdit++;
				}
				return getExtent().setBlock(location, block);
			}

		});
	}
}
