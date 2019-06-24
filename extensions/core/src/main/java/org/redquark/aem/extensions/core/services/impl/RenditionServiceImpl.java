package org.redquark.aem.extensions.core.services.impl;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.redquark.aem.extensions.core.services.RenditionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.gfx.Gfx;
import com.adobe.cq.gfx.Instructions;
import com.adobe.cq.gfx.Layer;
import com.adobe.cq.gfx.Plan;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.cq.dam.api.renditions.RenditionMaker;
import com.day.cq.dam.api.renditions.RenditionTemplate;
import com.day.cq.dam.api.thumbnail.ThumbnailConfig;
import com.day.cq.dam.commons.thumbnail.ThumbnailConfigImpl;
import com.day.cq.dam.commons.util.DamUtil;
import com.day.cq.dam.commons.util.OrientationUtil;

/**
 * @author Anirudh Sharma
 */
@Component(service = RenditionService.class, name = "Generate JPEG renditions for images", immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "= This service genetated the JPEG renditions of an image." })
public class RenditionServiceImpl implements RenditionService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// Injecting the reference of RenditionMaker which is responsible for creating
	// thumbnail, web and other renditions of DAM assets
	@Reference
	private RenditionMaker renditionMaker;

	// Injecting graphics rendering service
	@Reference
	private Gfx gfx;

	@Override
	public Asset generateJPEGRenditions(Resource resource, Integer width, Integer height) {

		// Checks whether the given resource is an asset, and if not, travels upwards
		// the resource hierarchy until a resource is an asset.
		Asset asset = DamUtil.resolveToAsset(resource);

		log.info("Generating JPEG renditions of the asset - {}", asset.getPath());

		RenditionTemplate[] templates = createRenditionTemplates(asset, width, height);

		renditionMaker.generateRenditions(asset, templates);

		return asset;
	}

	private RenditionTemplate[] createRenditionTemplates(Asset asset, Integer width, Integer height) {

		// Accessors for a thumbnail configuration
		ThumbnailConfig[] thumbnails = new ThumbnailConfig[1];

		thumbnails[0] = new ThumbnailConfigImpl(width, height, true);

		RenditionTemplate[] renditionTemplates = new RenditionTemplate[thumbnails.length];

		for (int i = 0; i < thumbnails.length; i++) {
			ThumbnailConfig thumbnailConfig = thumbnails[i];
			renditionTemplates[i] = createThumbnailTemplate(asset, thumbnailConfig.getWidth(),
					thumbnailConfig.getHeight(), thumbnailConfig.doCenter());
		}

		return renditionTemplates;
	}

	public class JPEGTemplate implements RenditionTemplate {

		private Plan plan;
		private String renditionName;
		private String mimeType;

		@Override
		public Rendition apply(Asset asset) {

			InputStream stream = null;

			try {
				stream = gfx.render(plan, asset.adaptTo(Resource.class).getResourceResolver());
				if (stream != null) {
					return asset.addRendition(renditionName, stream, mimeType);
				}
			} finally {
				IOUtils.closeQuietly(stream);
			}
			return null;
		}

	}

	private RenditionTemplate createThumbnailTemplate(Asset asset, int width, int height, boolean doCenter) {

		// Creating an instance of the JPEG template
		JPEGTemplate jpegTemplate = new JPEGTemplate();

		// Returns the asset's original as a Rendition. The original represents the
		// binary file that was initially uploaded as the asset. It is the unmodified
		// version of the asset. The original also represents a rendition of the asset,
		// as such its storage path is in the asset's rendition folder,
		final Rendition rendition = asset.getOriginal();

		// Name of the rendition created
		jpegTemplate.renditionName = "cq5dam.thumbnail." + width + "." + height + ".jpeg";

		// Setting the mimeType
		jpegTemplate.mimeType = "image/jpeg";

		// Setting the plan - Create an empty plan that can be set up from scratch.
		jpegTemplate.plan = gfx.createPlan();

		jpegTemplate.plan.layer(0).set("src", rendition.getPath());

		applyOrientation(OrientationUtil.getOrientation(asset), jpegTemplate.plan.layer(0));

		applyThumbnail(width, height, doCenter, jpegTemplate.mimeType, jpegTemplate.plan);

		return jpegTemplate;
	}

	private static void applyThumbnail(int width, int height, boolean doCenter, String mimeType, Plan plan) {

		// Instruction regarding the output view of the rendering plan.Defines the
		// output image format and other global flags.
		Instructions global = plan.view();

		global.set("wid", width);
		global.set("hei", height);
		global.set("rszfast", Boolean.FALSE);
		global.set("fit", doCenter ? "fit, 1" : "contrain, 1");

		String format = StringUtils.substringAfter(mimeType, "/");

		if ("png".equals(format) || "gif".equals(format) || "tif".equals(format)) {
			format = format + "-alpha";
		}

		global.set("fmt", format);

	}

	private static void applyOrientation(short exifOrientation, Layer layer) {

		switch (exifOrientation) {

		case OrientationUtil.ORIENTATION_MIRROR_HORIZONTAL:
			layer.set("flip", "lr");
			break;

		case OrientationUtil.ORIENTATION_ROTATE_180:
			layer.set("rotate", 180);
			break;

		case OrientationUtil.ORIENTATION_MIRROR_VERTICAL:
			layer.set("flip", "ud");
			break;

		case OrientationUtil.ORIENTATION_MIRROR_HORIZONTAL_ROTATE_270_CW:
			layer.set("flip", "lr");
			layer.set("rotate", 270);
			break;

		case OrientationUtil.ORIENTATION_ROTATE_90_CW:
			layer.set("rotate", 90);
			break;

		case OrientationUtil.ORIENTATION_MIRROR_HORIZONTAL_ROTATE_90_CW:
			layer.set("flip", "lr");
			layer.set("rotate", 90);
			break;

		case OrientationUtil.ORIENTATION_ROTATE_270_CW:
			layer.set("rotate", 270);
			break;
		}
	}
}
