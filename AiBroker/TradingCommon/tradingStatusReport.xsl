<?xml version="1.0" encoding="utf-8" ?>
<xsl:stylesheet version="2.0"
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:variable name="TR_BGCOLOR_BUY" select="'#90EE90'"/>
	<xsl:variable name="TR_BGCOLOR_SELL" select="'#DDA0DD'"/>
	<xsl:variable name="TR_BGCOLOR_ACTION" select="'#E8E8E8'"/>
	<xsl:variable name="TR_BGCOLOR_TIME" select="'#FFFFFF'"/>
	<xsl:variable name="TH_BGCOLOR" select="'#37577D'"/>
	<xsl:variable name="TH_FGCOLOR" select="'#FFFFFF'"/>
	<xsl:variable name="TD_2ND_COLUMN_BGCOLOR" select="'#E8E8E8'"/>

	<xsl:template match="/">
		<html>
			<head/>
			<body>
				<xsl:apply-templates />
			</body>
		</html>
	</xsl:template>

	<xsl:template match="orders">

		<table cellpadding="4" cellspacing="0">
			<tr>
				<td>Open positions:</td>
				<td align="right" bgcolor="{$TD_2ND_COLUMN_BGCOLOR}">
					<xsl:value-of select="openPositions" />
				</td>
			</tr>
			<tr>
				<td>Last enter bar time:</td>
				<td align="right" bgcolor="{$TD_2ND_COLUMN_BGCOLOR}">
					<xsl:value-of select="enterTime" />
				</td>
				<td>Last exit bar time:</td>
				<td align="right" bgcolor="{$TD_2ND_COLUMN_BGCOLOR}">
					<xsl:value-of select="exitTime" />
				</td>
			</tr>
			<tr>
				<td>Last enter signal price:</td>
				<td align="right" bgcolor="{$TD_2ND_COLUMN_BGCOLOR}">
					<xsl:value-of select="enterPrice" />
				</td>
				<td>Last exit signal price:</td>
				<td align="right" bgcolor="{$TD_2ND_COLUMN_BGCOLOR}">
					<xsl:value-of select="exitPrice" />
				</td>
			</tr>
			<tr>
				<td>Last enter operation:</td>
				<td align="right" bgcolor="{$TD_2ND_COLUMN_BGCOLOR}">
					<xsl:value-of select="enterOperation" />
				</td>
			</tr>
		</table>

		<br/>

		<center>
			<xsl:for-each select="order">
				<xsl:sort select="@index" data-type="number" order="descending"/>

				<table border="1" cellpadding="2" width="80%">
					<tr bgcolor="{$TH_BGCOLOR}">
						<th colspan="3" align="left">
							<font color="{$TH_FGCOLOR}">
								Order #<xsl:value-of select="id" />
							</font>
						</th>
						<th colspan="1" align="right">
							<font color="{$TH_FGCOLOR}">
								<xsl:value-of select="timeLimit" />
							</font>
						</th>
					</tr>

					<tr>
						<xsl:choose>
							<xsl:when test="operation = 'BUY'">
								<td colspan="4" align="left" bgcolor="{$TR_BGCOLOR_BUY}">
									<xsl:value-of select="symbol" />
								</td>
							</xsl:when>
							<xsl:otherwise>
								<td colspan="4" align="left" bgcolor="{$TR_BGCOLOR_SELL}">
									<xsl:value-of select="symbol" />
								</td>
							</xsl:otherwise>
						</xsl:choose>
					</tr>

					<tr align="center" bgcolor="{$TR_BGCOLOR_ACTION}">
						<td colspan="1" align="left">
							<xsl:value-of select="operation" />
						</td>
						<td colspan="1">
							<xsl:value-of select="volume" />
						</td>
						<td colspan="1">x</td>
						<td colspan="1">
							<xsl:value-of select="placePrice" />
						</td>
					</tr>

					<tr bgcolor="{$TR_BGCOLOR_TIME}">
						<td colspan="1" align="left">CREATED</td>
						<td colspan="3" align="center">
							<xsl:value-of select="created" />
						</td>
					</tr>

					<xsl:if test="count(./placed) > 0">
						<tr bgcolor="{$TR_BGCOLOR_TIME}">
							<td colspan="1" align="left">PLACED</td>
							<td colspan="3" align="center">
								<xsl:value-of select="placed" />
							</td>
						</tr>
					</xsl:if>

					<xsl:if test="count(./executed) > 0">
						<tr bgcolor="{$TR_BGCOLOR_TIME}">
							<td colspan="1" align="left">EXECUTED</td>
							<td colspan="3" align="center">
								<xsl:value-of select="executed" />
							</td>
						</tr>
						<tr bgcolor="{$TR_BGCOLOR_ACTION}">
							<td colspan="3" align="left">Execution price</td>
							<td colspan="1" align="center">
								<xsl:value-of select="ePrice" />
							</td>
						</tr>
					</xsl:if>

				</table>
				<br/>
			</xsl:for-each>
		</center>
	</xsl:template>

</xsl:stylesheet>
