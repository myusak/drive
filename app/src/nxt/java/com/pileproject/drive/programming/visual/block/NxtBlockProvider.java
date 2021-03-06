/*
 * Copyright (C) 2011-2015 PILE Project, Inc. <dev@pileproject.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pileproject.drive.programming.visual.block;

import com.pileproject.drive.programming.visual.block.repetition.LoopBlock;
import com.pileproject.drive.programming.visual.block.repetition.NTimesBlock;
import com.pileproject.drive.programming.visual.block.repetition.RepetitionBreakBlock;
import com.pileproject.drive.programming.visual.block.selection.IfNxtIsOutOfLineBlock;
import com.pileproject.drive.programming.visual.block.selection.IfNxtWasTouchedBlock;
import com.pileproject.drive.programming.visual.block.selection.IfThereWasALargeSoundBlock;
import com.pileproject.drive.programming.visual.block.sequence.BackwardSecBlock;
import com.pileproject.drive.programming.visual.block.sequence.ForwardSecBlock;
import com.pileproject.drive.programming.visual.block.sequence.SetLeftMotorSpeedBlock;
import com.pileproject.drive.programming.visual.block.sequence.SetRightMotorSpeedBlock;
import com.pileproject.drive.programming.visual.block.sequence.StopSecBlock;
import com.pileproject.drive.programming.visual.block.sequence.TurnLeftSecBlock;
import com.pileproject.drive.programming.visual.block.sequence.TurnRightSecBlock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NxtBlockProvider implements BlockProvider {

    @Override
    public List<Class<? extends BlockBase>> getSequenceBlockClasses() {

        // Because the lowest common ancestor of these blocks is SequenceBlockHasNumberText,
        // the stupid compiler infers this type as Class<? extends SequenceBlockHasNumberText>.
        // So without the ugly sentence, the code cannot be compiled.
        List<Class<? extends BlockBase>> list = Arrays.<Class<? extends BlockBase>> asList(
                ForwardSecBlock.class, BackwardSecBlock.class,
                TurnRightSecBlock.class, TurnLeftSecBlock.class,
                StopSecBlock.class,
                SetLeftMotorSpeedBlock.class, SetRightMotorSpeedBlock.class
        );

        return Collections.unmodifiableList(list);
    }

    @Override
    public List<Class<? extends BlockBase>> getRepetitionBlockClasses() {

        return Collections.unmodifiableList(Arrays.asList(
                LoopBlock.class, NTimesBlock.class, RepetitionBreakBlock.class
        ));
    }

    @Override
    public List<Class<? extends BlockBase>> getSelectionBlockClasses() {

        List<Class<? extends BlockBase>> list = Arrays.<Class<? extends BlockBase>> asList(
                IfNxtIsOutOfLineBlock.class,
                IfNxtWasTouchedBlock.class,
                IfThereWasALargeSoundBlock.class
        );

        return Collections.unmodifiableList(list);
    }
}
