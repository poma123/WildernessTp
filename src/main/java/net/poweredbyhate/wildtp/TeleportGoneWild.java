package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static net.poweredbyhate.wildtp.WildTP.checKar;
import static net.poweredbyhate.wildtp.WildTP.dataaaastorege;
import static net.poweredbyhate.wildtp.WildTP.instace;
import static net.poweredbyhate.wildtp.WildTP.isDebug;
import static net.poweredbyhate.wildtp.WildTP.newPlayersTeleported;
import static net.poweredbyhate.wildtp.WildTP.outdatedServer;
import static net.poweredbyhate.wildtp.WildTP.randomeWorlds;
import static net.poweredbyhate.wildtp.WildTP.useRandomeWorldz;

public class TeleportGoneWild {

    boolean needWait = instace.wamuppah > 0;
    int retries = instace.retries;
    boolean bypass;

    public void WildTeleport(final Player p, final String world, final boolean bypass) {
        this.bypass = bypass;
        World world1 = Bukkit.getWorld(world);
        if (!realTeleportt(p, world1, WildTP.maxXY, WildTP.minXY, WildTP.maxXY, WildTP.minXY))
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    WildTeleport(p, world, bypass);
                }
            }.runTaskLater(instace, 5L);
        }
    }

    public void WildTeleport(final Player p, final boolean bypass) {
        this.bypass = bypass;
        if (!realTeleportt(p, null, WildTP.maxXY, WildTP.minXY, WildTP.maxXY, WildTP.minXY))
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    WildTeleport(p, bypass);
                }
            }.runTaskLater(instace, 5L);
        }

    }

    public void WildTeleport(final Player p, final int maxX, final int minX, final int maxZ, final int minZ, final boolean bypass)
    {
        this.bypass = bypass;
        if (!realTeleportt(p, null, maxX, minX, maxZ, minZ))
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    WildTeleport(p, maxX, minX, maxZ, minZ, bypass);
                }
            }.runTaskLater(instace, 5L);
        }
    }

    public boolean realTeleportt(final Player p, World world, int maxX, int minX, int maxZ, int minZ) {
        retries--;

        WildTP.debug("Wild teleport called for " + p.getName());
        if (WildTP.checKar.isInCooldown(p.getUniqueId())) {
            WildTP.debug("In cooldown: yes");
            p.sendMessage(TooWildForEnums.translate(TooWildForEnums.COOLDOWN.replace("%TIME%", WildTP.checKar.getTimeLeft(p))));
            return true;
        }

        Location locNotFinal;
        if (instace.cash != null && ((world == null && ((useRandomeWorldz && randomeWorlds.contains(instace.cash.getWorld().getName())) || (!useRandomeWorldz && p.getWorld() == instace.cash.getWorld()))) || world == instace.cash.getWorld()) && n0tAGreifClam(instace.cash, p))
        {
            locNotFinal = instace.cash;
            instace.cash = null;
        }
        else
            locNotFinal = getRandomeLocation(world, p, maxX, minX, maxZ, minZ);
        if (locNotFinal == null) {
            if (retries >= 0)
                return false;
            p.sendMessage(TooWildForEnums.translate(TooWildForEnums.NO_LOCATION));
            WildTP.debug("No suitable locations found");
            return true;
        }

        PreWildTeleportEvent preWildTeleportEvent = new PreWildTeleportEvent(p, locNotFinal);
        WildTP.debug("Calling preTeleportEvent");
        Bukkit.getServer().getPluginManager().callEvent(preWildTeleportEvent);
        WildTP.debug("Called preWildTeleportEvent");
        if (preWildTeleportEvent.isCancelled()) {
            WildTP.debug("preWildTeleport Cancelled");
            return !preWildTeleportEvent.isRetry();
        }

        if (instace.dr0p1n && !bonelessIceScream(locNotFinal)) {
            WildTP.debug("Drop in feature enabled: Setting y=256");
            locNotFinal.setY(256);
            locNotFinal.setPitch(64);
            OuchieListener.plsSaveDisDood(p);
        }
        final Location loc = locNotFinal;
        if (needWait && !bypass) {
            WildTP.debug("Player needs to wait more");
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', TooWildForEnums.WAIT_MSG.replace("{wait}",String.valueOf(instace.wamuppah))));
            TooCool2Teleport.addPlayer(p, goWild(p, loc, instace.wamuppah*20L, maxX, minX, maxZ, minZ));
        }
        else
            goWild(p, loc, 0L, maxX, minX, maxZ, minZ);
        return true;
    }

    public Location getRandomeLocation(World world, Player player, int maxX, int minX, int maxZ, int minZ) {
        if (world == null)
        {
            if (instace.useRandomeWorldz)
                world = getRandomeWorld(instace.randomeWorlds);
            else if (player != null)
                world = player.getWorld();
            else
                return null;
        }
        for (int i = 0; i < 4; i++) {
            Location loco = new Location(world, r4nd0m(maxX, minX), 5, r4nd0m(maxZ, minZ));
            if (bonelessIceScream(loco))
                loco = netherLocation(loco);
            if (loco == null)
                continue;
            if (!instace.getConfig().getStringList("BlockedBiomes").contains(loco.getBlock().getBiome().toString())) {
                if (!bonelessIceScream(loco))
                    loco.setY(world.getHighestBlockYAt(loco) - 1);
                loco.setX(loco.getX() + 0.5D);
                loco.setZ(loco.getZ() + 0.5D);

                if (n0tAB4dB10ck(loco, true))
                {
                    if (!n0tAGreifClam(loco, player))
                        continue;
                    loco.setY(loco.getY() + 1);
                    if (n0tAB4dB10ck(loco, false))
                    {
                        loco.setY(loco.getY() + 2);
                        return loco;
                    }
                }
            }
        }
        return null;
    }

    public World getRandomeWorld(ConfigurationSection imDaMap) {
        Map<Integer, World> hesDaMap = new LinkedHashMap<>();
        Integer totalChance = 0;
        for (String worldString : imDaMap.getKeys(false)) {
            World world = Bukkit.getWorld(worldString);
            if (world == null) {
                instace.getLogger().warning("World \"" + worldString + "\" does not exist. We recommend removing the world from randomWorlds in config.yml");
                continue;
            }
            totalChance = totalChance + (Integer)imDaMap.get(worldString);
            hesDaMap.put(totalChance, world);
        }
        int daChosenOne = r4nd0m(totalChance, 0);
        for (Integer blah : hesDaMap.keySet()) {
            if (blah >= daChosenOne)
                return hesDaMap.get(blah);
        }
        return null;
    }

    public static int r4nd0m(int max, int min) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public boolean n0tAGreifClam(Location l0c0, Player player) {
        if (instace.useExperimentalChekar && player != null)
        {
            try
            {
                player.setMetadata("nocheat.exempt", new FixedMetadataValue(instace, true));
                BlurredBlockBreakEvent iHopePluginsDontFreakOutOverThis = new BlurredBlockBreakEvent(l0c0.getBlock(), new JohnBonifield(player));
                instace.getServer().getPluginManager().callEvent(iHopePluginsDontFreakOutOverThis);
                player.removeMetadata("nocheat.exempt", instace);
                return !iHopePluginsDontFreakOutOverThis.isExposed();
            }
            catch (Throwable rock)
            {
                if (!isDebug)
                    instace.getLogger().warning("Unable to useExperimentalClaimCheck. For more details, set the following in the config.yml: debug: true");
                else
                    rock.printStackTrace();

            }
        }

        if (dataaaastorege != null)
        {
            return instace.dataaaastorege.getClaimAt(l0c0, true, null) == null;
        }

        return true;
    }

    public boolean n0tAB4dB10ck(Location l0c0, boolean checkAir) {
        Material blockType = l0c0.getBlock().getType();
        return blockType != Material.LAVA &&
                blockType != Material.STATIONARY_LAVA &&
                (outdatedServer || blockType != Material.MAGMA) &&
                blockType != Material.CACTUS &&
                blockType != Material.FIRE &&
                (!checkAir || blockType != Material.AIR);
    }

    private boolean bonelessIceScream(Location location)
    {
        if (location.getWorld().getEnvironment() != World.Environment.NETHER)
            return false;
        location = location.clone();
        location.setY(128);
        return location.getBlock().getType() == Material.BEDROCK;
    }

    Location netherLocation(Location l0c0) {
        l0c0.setY(1);
        while (l0c0.getY() < 127)
        {
            //Is current block an air block?
            if (l0c0.getBlock().getType() != Material.AIR) {
                l0c0 = l0c0.getBlock().getRelative(BlockFace.UP).getLocation();
                continue;
            }
            //Is block above also an air block?
            if (l0c0.getBlock().getRelative(BlockFace.UP).getType() != Material.AIR) {
                l0c0 = l0c0.getBlock().getRelative(BlockFace.UP).getLocation();
                continue;
            }
            l0c0 = l0c0.getBlock().getRelative(BlockFace.DOWN).getLocation();
            return l0c0.getBlock().getRelative(BlockFace.DOWN).getLocation();
        }
        return null;
    }
    public BukkitTask goWild(final Player p, final Location loc, final Long time, final int maxX, final int minX, final int maxZ, final int minZ)
    {
        return new BukkitRunnable() {
            @Override
            public void run() {
                WildTP.debug("Starting Random Teleport");
                if (!bypass) {
                    if (needWait && !TooCool2Teleport.isCold(p))
                        return;
                    TooCool2Teleport.makeHot(p);
                }
                WildTP.debug("Teleporting " + p.getName());
                if (!p.teleport(loc))
                {
                    WildTP.debug("teleport was canceled.");
                    return;
                }
                WildTP.debug(p.getName()+ " Teleported");
                WildTP.debug(p.getName() + " Adding to cooldown");
                WildTP.checKar.addKewlzDown(p.getUniqueId());
                WildTP.debug("Added to cooldown " + p.getUniqueId());
                PostWildTeleportEvent postWildTeleportEvent = new PostWildTeleportEvent(p);
                Bukkit.getServer().getPluginManager().callEvent(postWildTeleportEvent);
                new Cashier(getRandomeLocation(null, p, maxX, minX, maxZ, minZ));
            }
        }.runTaskLater(instace, time);
    }
}
