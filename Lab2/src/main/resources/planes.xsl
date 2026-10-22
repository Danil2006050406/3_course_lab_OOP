<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes" encoding="UTF-8"/>
    <xsl:key name="originKey" match="Aircraft" use="Origin"/>
    <xsl:template match="/Plane">
        <PlanesByOrigin>
            <xsl:for-each select="Aircraft[generate-id() = generate-id(key('originKey', Origin)[1])]">
                <xsl:sort select="Origin"/>
                <Country>
                    <xsl:attribute name="name"><xsl:value-of select="Origin"/></xsl:attribute>
                    <xsl:for-each select="key('originKey', Origin)">
                        <xsl:sort select="Model"/>
                        <Aircraft>
                            <xsl:copy-of select="@id"/>
                            <Model><xsl:value-of select="Model"/></Model>
                            <Price><xsl:value-of select="Price"/></Price>
                        </Aircraft>
                    </xsl:for-each>
                </Country>
            </xsl:for-each>
        </PlanesByOrigin>
    </xsl:template>
</xsl:stylesheet>
