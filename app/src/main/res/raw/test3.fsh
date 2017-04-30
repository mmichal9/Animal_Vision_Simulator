#extension GL_OES_standard_derivatives : enable
precision mediump float;

uniform vec3                iResolution;
uniform vec3                iChannelResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;


vec2 barrelDistort(vec2 pos, float power)
{
	float t = atan(pos.y, pos.x);
	float r = pow(length(pos), power);
	pos.x   = r * cos(t);
	pos.y   = r * sin(t);
	return 0.5 * (pos + 1.0);
}

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
	vec2 q  = fragCoord.xy / iResolution.xy;
	vec2 p  = -1.0 + 2.0*q;
	float d = length(p);

	float s = 1.0 - min(1.0, d*d);

	float t = 1.;
	float barrel_pow = 1.0 + 0.5 * (1.0 + cos(t));
	p = barrelDistort(p, barrel_pow);

	fragColor = texture2D(iChannel0, s * (p-q) + q );
}

void main() {
    mainImage(gl_FragColor, texCoord.xy*iResolution.xy);
}
