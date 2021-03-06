/*
 *  This file is part of Cubic Chunks Mod, licensed under the MIT License (MIT).
 *
 *  Copyright (c) 2015 contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package cubicchunks;

import cubicchunks.lighting.FirstLightProcessor;
import cubicchunks.world.ICubicWorldServer;
import cubicchunks.worldgen.DependentGeneratorStage;
import cubicchunks.worldgen.GeneratorStageRegistry;
import cubicchunks.worldgen.GeneratorStage;
import cubicchunks.worldgen.IndependentGeneratorStage;
import cubicchunks.worldgen.dependency.RegionDependency;
import cubicchunks.worldgen.generator.custom.CustomFeatureProcessor;
import cubicchunks.worldgen.generator.custom.CustomPopulationProcessor;
import cubicchunks.worldgen.generator.custom.CustomTerrainProcessor;
import net.minecraft.util.math.Vec3i;

public class CustomCubicChunksWorldType extends BaseCubicWorldType {

	public CustomCubicChunksWorldType() {
		super("CustomCubic");
	}

	@Override public void registerWorldGen(ICubicWorldServer world, GeneratorStageRegistry generatorStageRegistry) {
		// init the world's GeneratorStageRegistry
		GeneratorStage terrain = new IndependentGeneratorStage("terrain");

		GeneratorStage features = new IndependentGeneratorStage("features");

		DependentGeneratorStage lighting = new DependentGeneratorStage("lighting", null);
		lighting.setCubeDependency(new RegionDependency(lighting, 2));

		DependentGeneratorStage population = new DependentGeneratorStage("population", null);
		population.setCubeDependency(new RegionDependency(population, new Vec3i(0, 0, 0), new Vec3i(1, 1, 1)));

		generatorStageRegistry.addStage(terrain, new CustomTerrainProcessor(world));
		generatorStageRegistry.addStage(features, new CustomFeatureProcessor());
		generatorStageRegistry.addStage(lighting, new FirstLightProcessor(lighting, world));
		generatorStageRegistry.addStage(population, new CustomPopulationProcessor(world));
	}

	public static void create() {
		new CustomCubicChunksWorldType();
	}
}
