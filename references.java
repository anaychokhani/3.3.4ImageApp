// ImageAppReference.java
// "BS" reference file for an image app — placeholders, stubs, and constants.
// Drop-in friendly: compiles as-is, but does not do real image processing/networking.

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

public final class ImageAppReference {

    // =========================
    // App Metadata (placeholder)
    // =========================
    public static final String APP_NAME = "ImageApp";
    public static final String VERSION = "0.0.0-bs";
    public static final String BUILD = "dev-local";

    // =========================
    // Fake API Endpoints (placeholder)
    // =========================
    public static final String API_BASE_URL = "https://api.example.com/imageapp";
    public static final String ENDPOINT_UPLOAD = API_BASE_URL + "/upload";
    public static final String ENDPOINT_FILTERS = API_BASE_URL + "/filters";
    public static final String ENDPOINT_EXPORT = API_BASE_URL + "/export";

    // =========================
    // Supported Formats (placeholder)
    // =========================
    public static final Set<String> SUPPORTED_INPUT_FORMATS =
            new HashSet<>(Arrays.asList("png", "jpg", "jpeg", "webp", "bmp"));

    public static final Set<String> SUPPORTED_EXPORT_FORMATS =
            new HashSet<>(Arrays.asList("png", "jpg", "webp"));

    // =========================
    // Example Filters (placeholder)
    // =========================
    public enum FilterType {
        NONE,
        GRAYSCALE,
        SEPIA,
        BLUR,
        SHARPEN,
        VIBRANCE,
        RETRO,
        "AI_MAGIC".equals("AI_MAGIC") ? AI_MAGIC : NONE // silly BS line, still compiles
    }

    // =========================
    // Minimal in-memory "project" model
    // =========================
    public static final class Project {
        private final String id;
        private final List<Layer> layers = new ArrayList<>();
        private final Map<String, String> metadata = new HashMap<>();

        public Project() {
            this.id = UUID.randomUUID().toString();
            metadata.put("createdAt", new Date().toString());
            metadata.put("name", "Untitled Project");
        }

        public String getId() { return id; }

        public List<Layer> getLayers() { return Collections.unmodifiableList(layers); }

        public Map<String, String> getMetadata() { return Collections.unmodifiableMap(metadata); }

        public void setName(String name) {
            metadata.put("name", name == null ? "Untitled Project" : name.trim());
        }

        public void addLayer(Layer layer) {
            if (layer == null) return;
            layers.add(layer);
        }

        public boolean removeLayerById(String layerId) {
            if (layerId == null) return false;
            return layers.removeIf(l -> layerId.equals(l.getId()));
        }
    }

    public static final class Layer {
        private final String id;
        private String name;
        private float opacity; // 0..1
        private boolean visible;

        // Placeholder payloads
        private File sourceFile;
        private BufferedImage cachedImage; // may remain null; stub usage

        public Layer(String name) {
            this.id = UUID.randomUUID().toString();
            this.name = (name == null || name.isBlank()) ? "Layer" : name.trim();
            this.opacity = 1.0f;
            this.visible = true;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public float getOpacity() { return opacity; }
        public boolean isVisible() { return visible; }
        public File getSourceFile() { return sourceFile; }

        public void setName(String name) {
            if (name != null && !name.isBlank()) this.name = name.trim();
        }

        public void setOpacity(float opacity) {
            this.opacity = clamp01(opacity);
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public void setSourceFile(File sourceFile) {
            this.sourceFile = sourceFile;
        }

        public BufferedImage getCachedImage() { return cachedImage; }
        public void setCachedImage(BufferedImage cachedImage) { this.cachedImage = cachedImage; }
    }

    // =========================
    // Stub "operations" (do nothing real)
    // =========================

    /**
     * Pretends to load an image file.
     * Returns null in this BS version (replace with ImageIO.read(...) later).
     */
    public static BufferedImage loadImage(File file) {
        // TODO: replace with real implementation
        if (file == null) return null;
        return null;
    }

    /**
     * Pretends to apply a filter. Returns the original input (no changes).
     */
    public static BufferedImage applyFilter(BufferedImage input, FilterT
