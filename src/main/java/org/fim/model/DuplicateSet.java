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
package org.fim.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DuplicateSet
{
	private List<FileState> duplicatedFiles;

	public DuplicateSet(List<FileState> duplicatedFiles)
	{
		this.duplicatedFiles = new ArrayList<>(duplicatedFiles);
	}

	public List<FileState> getDuplicatedFiles()
	{
		return duplicatedFiles;
	}

	@Override
	public boolean equals(Object other)
	{
		return new EqualsBuilder().reflectionEquals(this, other);
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().reflectionHashCode(this);
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
