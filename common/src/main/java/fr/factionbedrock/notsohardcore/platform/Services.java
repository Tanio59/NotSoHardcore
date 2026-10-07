package fr.factionbedrock.notsohardcore.platform;

import fr.factionbedrock.notsohardcore.NotSoHardcore;
import fr.factionbedrock.notsohardcore.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

// Service loaders are a built-in Java feature that allow us to locate implementations of an interface that vary from one
// environment to another. The common code uses IPlatformHelper, and each mod loader provides its own implementation
// (FabricPlatformHelper, NeoForgePlatformHelper) declared in its META-INF/services folder.
public class Services
{
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    public static <T> T load(Class<T> clazz)
    {
        final T loadedService = ServiceLoader.load(clazz, Services.class.getClassLoader())
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        NotSoHardcore.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
