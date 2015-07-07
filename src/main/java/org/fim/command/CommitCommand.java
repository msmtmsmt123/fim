/*
 * This file is part of Fim - File Integrity Manager
 *
 * Copyright (C) 2015  Etienne Vrignaud
 *
 * Fim is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Fim is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Fim.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.fim.command;

import org.fim.internal.StateComparator;
import org.fim.internal.StateGenerator;
import org.fim.internal.StateManager;
import org.fim.model.CompareResult;
import org.fim.model.FimOptions;
import org.fim.model.State;

public class CommitCommand extends AbstractCommand
{
	@Override
	public String getCmdName()
	{
		return "commit";
	}

	@Override
	public String getShortCmdName()
	{
		return "ci";
	}

	@Override
	public String getDescription()
	{
		return "Commit the current directory State";
	}

	@Override
	public void execute(FimOptions fimOptions) throws Exception
	{
		StateGenerator generator = new StateGenerator(fimOptions.getThreadCount(), fimOptions.getCompareMode());
		StateManager manager = new StateManager(fimOptions.getStateDir(), fimOptions.getCompareMode());
		StateComparator comparator = new StateComparator(fimOptions.getCompareMode());

		fastCompareNotSupported(fimOptions.getCompareMode());

		State lastState = manager.loadLastState();
		State currentState = generator.generateState(fimOptions.getMessage(), fimOptions.getBaseDirectory());
		CompareResult result = comparator.compare(lastState, currentState).displayChanges(fimOptions.isVerbose());
		if (result.somethingModified())
		{
			System.out.println("");
			if (fimOptions.isAlwaysYes() || confirmCommand("commit"))
			{
				manager.createNewState(currentState);
			}
			else
			{
				System.out.println("Nothing committed");
			}
		}
	}
}